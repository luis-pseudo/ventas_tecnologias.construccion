package mx.uv.tcsw.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "mx.uv.tcsw", importOptions = ImportOption.DoNotIncludeTests.class)
public class HexagonalTest {
    @ArchTest
    public static final ArchRule dominio_no_depende_del_exterior = noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat()
        .resideInAnyPackage("..application..", "..adapter..", "javax.swing..");

    @ArchTest
    public static final ArchRule nucleo_sin_spring = noClasses()
        .that().resideInAnyPackage("..domain..", "..application..")
        .should().dependOnClassesThat()
        .resideInAnyPackage("org.springframework..", "javax.servlet..");

    @ArchTest
    public static final ArchRule dominio_sin_persistencia = noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat()
        .resideInAnyPackage("jakarta.persistence..", "javax.persistence..", "org.hibernate..");
}