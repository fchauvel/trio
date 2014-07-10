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
/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * TRIO is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * TRIO is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.diversify.trio.acceptance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Helpers used in acceptance tests
 */
public class ToolBox {

    /**
     * UNzip a file into the given directory
     *
     * @param pathToZipFile the file that must be unzipped
     * @param destination the directory where the content should be created
     */
    public static void unzipTo(final String pathToZipFile, final String destination) throws IOException {
        final ZipFile zipFile = new ZipFile(pathToZipFile);
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            final ZipEntry entry = entries.nextElement();
            if (!entry.isDirectory()) {
                final File entryDestination = new File(destination, entry.getName());
                entryDestination.getParentFile().mkdirs();
                final InputStream in = zipFile.getInputStream(entry);
                Files.copy(in, Paths.get(entryDestination.getPath()), StandardCopyOption.REPLACE_EXISTING);
                in.close();
            }
        }
    }

    public static String randomName(int length) {
        final Random random = new Random();
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            boolean lowercase = random.nextBoolean();
            int choice = random.nextInt(26);
            int asciiCode = (lowercase) ? 97 + choice : 65 + choice;
            builder.append((char) asciiCode);
        }
        return builder.toString();
    }

    public static void delete(final String path) {
        final File file = new File(path);
        if (file.isDirectory()) {
            final File[] content = file.listFiles();
            for (File eachFile: content) {
                delete(eachFile.getPath());
            }
            file.delete();
        } else {
            file.delete();
        }
    }

}
