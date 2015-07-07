package test.com.epam.edu.mobilservice.mobile.part;

import com.epam.edu.mobilservice.mobile.part.MobilePartConverter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class MobilePartConverterTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConvertWithNullInput() {
        MobilePartConverter.convert(null);
        fail();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testConvertWithInvalidManufacturer() {
        MobilePartConverter.convert("NOKIA Display");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testConvertWithInvalidMobilePart() {
        MobilePartConverter.convert("APPLE Monitor");
    }

    @DataProvider(name = "manufacturers")
    public Object[][] getManufacturers() {
        return new String[][]{
                new String[]{"APPLE Keyboard", "APPLE"},
                new String[]{"SAMSUNG Keyboard", "SAMSUNG"},
                new String[]{"HTC Keyboard", "HTC"},
                new String[]{"HUAWEI Keyboard", "HUAWEI"}
        };
    }

    @Test(dataProvider = "manufacturers")
    public void testConvertManufacturers(String actual, String expected) {
        assertEquals(MobilePartConverter.convert(actual).getManufacturer().toString(), expected);
    }

    @DataProvider(name = "mobileParts")
    public Object[][] getMobileParts() {
        return new String[][]{
                new String[]{"APPLE iPhone 5s Motherboard", "APPLE iPhone 5s Motherboard"},
                new String[]{"HUAWEI Ascend P2 Motherboard", "HUAWEI Ascend P2 Motherboard"},
                new String[]{"SAMSUNG Galaxy S3 Motherboard", "SAMSUNG Galaxy S3 Motherboard"},
                new String[]{"SAMSUNG Power Switch", "SAMSUNG Power Switch"},
                new String[]{"HTC Power Switch", "HTC Power Switch"},
                new String[]{"SAMSUNG Volume Button", "SAMSUNG Volume Button"}
        };
    }

    @Test(dataProvider = "mobileParts")
    public void testConvertMobileParts(String actual, String expected) {
        assertEquals(MobilePartConverter.convert(actual).getName(), expected);
    }
}