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


package eu.diversify.trio;

import java.io.IOException;
import java.lang.System;

/**
 * Entry point of the application
 */
public class Main {

    public static void main(String[] args) {
        try {
            Command.from(args).execute(new Trio());
        
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            System.out.println(Command.usage());
        
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            
        }
    }
    
}
