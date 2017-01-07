package io.mstream.addresses.api.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(Parameterized.class)
public class PostcodeValidatorTest {

    private final String postcode;
    private final boolean valid;

    private PostcodeValidator instance;

    public PostcodeValidatorTest(String postcode, boolean valid) {
        this.postcode = postcode;
        this.valid = valid;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"W60LG", true},
                {"SW1A2AA", true},
                {"BT486DQ", true},
                {"EC1A1BB", true},
                {"W1A0AX", true},
                {"M11AE", true},
                {"B338TH", true},
                {"CR26XH", true},
                {"DN551PT", true},
                {"", false},
                {"W60LG!", false},
                {"W60_LG", false},
                {"W60LGB", false},
                {"W60L", false},
                {"EC1A1BBB", false}
        });
    }

    @Before
    public void setUp() throws Exception {
        instance = new PostcodeValidator();
    }

    @Test
    public void test() {
        ValidationResult validationResult = instance.validate(postcode);
        assertThat(validationResult.isValid()).isEqualTo(valid);
    }

}