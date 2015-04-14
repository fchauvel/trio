
package eu.diversify.trio.graph.generator.barabasi;

import eu.diversify.trio.graph.generator.barabasi.BASetup;
import java.util.Properties;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import org.junit.Test;

/**
 * Specification of the barabasi & Albert setup
 */
public class BarabasiAlbertSetupTest {

    private static final double ERROR_TOLERANCE = 1e-6;
    
    @Test
    public void shouldExtractBetaFromProperties() {
        final BASetup setup = new BASetup();
        final double beta = 0.67;     
        
        final Properties values = new Properties();
        values.put(BASetup.BETA_KEY, String.valueOf(beta));
        setup.updateWith(values);
        
        assertThat(setup.getBeta(), is(closeTo(beta, ERROR_TOLERANCE)));
    }
    
     @Test
    public void shouldExtractAlphaFromProperties() {
        final BASetup setup = new BASetup();
        final double alpha = 0.67;     
        
        final Properties values = new Properties();
        values.put(BASetup.ALPHA_KEY, String.valueOf(alpha));
        setup.updateWith(values);
        
        assertThat(setup.getAlpha(), is(closeTo(alpha, ERROR_TOLERANCE)));
    }
    
}
