/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 */

package eu.diversify.trio.unit.core.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.validation.Validator;

import static eu.diversify.trio.core.Evaluation.*;
import static eu.diversify.trio.core.storage.Builder.build;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
@RunWith(JUnit4.class)
public class ValidatorTest {
    
    
    @Test
    public void shouldDetectUnknownDependencies() {
        final String inputText = 
                "components:\n"
                + " - AA requires BB and CC\n"
                + " - BB requires DD \n" // Error: DD is never defined 
                + " - CC\n";
        
        Assembly system = build().systemFrom(inputText);
        
        Validator validity = new Validator();
        evaluate(validity).on(system);
        
        assertThat("Should have one error!", validity.hasError());
        
    }
    
    
    @Test
    public void shouldDetectUnknownComponentInTags() {
        final String inputText = 
                "components:\n"
                + " - AA requires BB and CC\n"
                + " - BB requires CC \n"
                + " - CC\n"
                + "tags:\n"
                + " - 'mytag' on AA, DD\n"; // Error, DD is never defined
        
        Assembly system = build().systemFrom(inputText);
        
        Validator validity = new Validator();
        evaluate(validity).on(system);
        
        assertThat("Should have one error!", validity.hasError());
        
    }
    
    @Test
    public void shouldValidateCorrectSystems() {
        final String inputText = 
                "components:\n"
                + " - AA requires BB and CC\n"
                + " - BB requires CC\n"
                + " - CC\n"
                + "tags:"
                + " - 'mytag' on AA, BB";
        
        Assembly system = build().systemFrom(inputText);
        
        Validator validity = new Validator();
        evaluate(validity).on(system);
        
        assertThat("Should have no error!", !validity.hasError());
        
    }
    
}
