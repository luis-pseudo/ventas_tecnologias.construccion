package mx.uv.tcsw.architecture;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner; 
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@RunWith(ArchUnitRunner.class)   
@AnalyzeClasses(packages = "mx.uv.tcsw")
public class HexagonalTest {
    @ArchTest
    public static final ArchRule dominio_no_depende_del_exterior = noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat()
        .resideInAnyPackage("..application..", "..adapter..", "javax.swing..");
}