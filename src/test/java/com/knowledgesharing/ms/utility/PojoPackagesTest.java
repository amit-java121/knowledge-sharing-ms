package com.knowledgesharing.ms.utility;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class PojoPackagesTest {
    private static final String POJO_PACKAGE_ENTITIES = "com.knowledgesharing.ms.entities";
    private static final String POJO_PACKAGE_DTO = "com.knowledgesharing.ms.datatransfer";
    private List<PojoClass> pojoClassesEntities = null;
    private List<PojoClass> pojoClassesDto = null;

    @BeforeEach
    void before() {
        pojoClassesEntities = PojoClassFactory.getPojoClasses(POJO_PACKAGE_ENTITIES, new FilterPackageInfo());
        pojoClassesDto = PojoClassFactory.getPojoClasses(POJO_PACKAGE_DTO, new FilterPackageInfo());
    }

    @Test
    void testPojoStructureAndBehavior() {
        Validator validatorModel = ValidatorBuilder.create()
                .with(new SetterTester()).with(new GetterTester()).build();
        validatorModel.validate(pojoClassesEntities);
        validatorModel.validate(pojoClassesDto);
    }

}