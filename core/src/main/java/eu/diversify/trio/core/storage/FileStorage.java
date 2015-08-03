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
package eu.diversify.trio.core.storage;

import eu.diversify.trio.core.storage.parsing.SyntaxError;
import eu.diversify.trio.core.Assembly;
import static eu.diversify.trio.core.Evaluation.evaluate;
import static eu.diversify.trio.core.storage.parsing.Builder.build;
import eu.diversify.trio.core.validation.InvalidSystemException;
import eu.diversify.trio.core.validation.Validator;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Storage in flat files
 */
public class FileStorage implements Storage {

    private final String path;
    private Validator validity;

    public FileStorage(String path) {
        validity = new Validator();
        this.path = path;
    }

    public Assembly first() throws StorageError {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            final Assembly architecture = build().systemFrom(fileInputStream);
            evaluate(validity).on(architecture);
            validity.check();
            return architecture;

        } catch (IOException ex) {
            throw new RuntimeException("Unable to open the stream from '" + path + "'", ex);

        } catch (SyntaxError ex) {
            throw new StorageError("Unable to fetch architecture from " + path, ex);

        } catch (InvalidSystemException ex) {
            throw new StorageError("Unable to fetch architecture from " + path, ex);

        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();

                } catch (IOException ex) {
                    throw new RuntimeException("Unable to close the stream from '" + path + "'", ex);
                }
            }
        }
    }

}
