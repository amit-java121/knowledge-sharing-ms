package com.knowledgesharing.ms.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Config class for use actuator/info, we use some variables.
 *
 * <b>Build - Maven GAV</b>
 * GroupId e ArtifactId: we can find them in application.yml with variable info.build.*
 * Version: we read it at runtime from MANIFEST.
 *
 * <b>App</b>
 * We can find them in application.yml with variable spring.application.*
 */
@Component
public class SpringConfigInfo implements InfoContributor {

	@Value("${info.build.artifactId}")
	private String artifactId;

	@Value("${info.build.groupId}")
	private String groupId;

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${spring.application.description}")
	private String applicationDescription;

	@Autowired
	private ApplicationContext ctx;

	@Override
	public void contribute(Info.Builder builder) {
		// Setting context
		Map<String, Object> contextDetails = new HashMap<>();
		contextDetails.put("bean-definition-count", ctx.getBeanDefinitionCount());
		contextDetails.put("startup-date", ctx.getStartupDate());
		builder.withDetail("context", contextDetails);

		// Settings app
		Map<String, String> appDetails = new HashMap<>();
		appDetails.put("name", applicationName);
		appDetails.put("description", applicationDescription);
		builder.withDetail("app", appDetails);

		// Settings GAV
		String version = getClass().getPackage().getImplementationVersion();

		Map<String, String> buildDetails = new HashMap<>();
		buildDetails.put("groupId", groupId);
		buildDetails.put("artifactId", artifactId);
		buildDetails.put("version", version);
		builder.withDetail("build", buildDetails);

		// Settings Spring profiles
		Map<String, String[]> springProfiles = new HashMap<>();
		springProfiles.put("Default Profile", ctx.getEnvironment().getDefaultProfiles());
		springProfiles.put("Active Profile", ctx.getEnvironment().getActiveProfiles());
		builder.withDetail("Spring Profiles", springProfiles);
	}

}
