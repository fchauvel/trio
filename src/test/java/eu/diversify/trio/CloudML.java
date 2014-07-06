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

import org.cloudml.core.builders.DeploymentBuilder;

import static org.cloudml.core.builders.Commons.*;

/**
 * Provide some reusable CloudML models
 */
public class CloudML {

    public static DeploymentBuilder aSingleVM() {
        return CloudML.aSingleVM("Anonymous");
    }

    public static DeploymentBuilder aSingleVM(String vmName) {
        return aDeployment()
                .with(aProvider().named("Ec2"))
                .with(aVM()
                        .named(vmName)
                        .providedBy("Ec2"));

    }

    public static DeploymentBuilder twoIndependentVMs() {
        return twoIndependentVMs("vm1", "vm2");
    }

    public static DeploymentBuilder twoIndependentVMs(String vm1, String vm2) {
        return aDeployment()
                .with(aProvider().named("Ec2"))
                .with(aVM()
                        .named(vm1)
                        .providedBy("Ec2"))
                .with(aVM()
                        .named(vm2)
                        .providedBy("Ec2"));
    }

    public static DeploymentBuilder anAppOnAVm() {
        return anAppOnAVm("My App", "My VM");
    }

    public static DeploymentBuilder anAppOnAVm(String appName, String vmName) {
        return aDeployment()
                .with(aProvider().named("Ec2"))
                .with(aVM()
                        .named(vmName)
                        .providedBy("Ec2")
                        .with(aProvidedExecutionPlatform()
                                .offering("OS", "Linux")))
                .with(anInternalComponent()
                        .named(appName)
                        .with(aRequiredExecutionPlatform()
                                .demanding("OS", "Linux")));
    }

    public static DeploymentBuilder anAppAndItsDependencyOnAVm() {
        return anAppAndItsDependencyOnAVm("App", "Dep", "VM");
    }

    public static DeploymentBuilder anAppAndItsDependencyOnAVm(String appName, String depName, String vmName) {
        return aDeployment()
                .with(aProvider().named("Ec2"))
                .with(aVM()
                        .named(vmName)
                        .providedBy("Ec2")
                        .with(aProvidedExecutionPlatform()
                                .offering("OS", "Linux")))
                .with(anInternalComponent()
                        .named(depName)
                        .with(aProvidedPort()
                                .named("pp")
                                .remote())
                        .with(aRequiredExecutionPlatform()
                                .demanding("OS", "Linux")))
                .with(anInternalComponent()
                        .named(appName)
                        .with(aRequiredPort()
                                .named("rp")
                                .mandatory()
                                .remote())
                        .with(aRequiredExecutionPlatform()
                                .demanding("OS", "Linux")))
                .with(aRelationship()
                        .named("Connection")
                        .from(appName, "rp")
                        .to(depName, "pp"));

    }

    public static DeploymentBuilder anAppAndNCandidateVMs(int variantCount) {
        DeploymentBuilder builder = aDeployment()
                .with(aProvider().named("Ec2"))
                .with(anInternalComponent()
                        .named("App")
                        .with(aRequiredExecutionPlatform()
                                .demanding("OS", "Linux")));

        for (int i = 0; i < variantCount; i++) {
            builder.with(aVM()
                    .named(String.format("VM no. %d", i + 1))
                    .providedBy("Ec2")
                    .with(aProvidedExecutionPlatform()
                            .offering("OS", "Linux")));

        }

        return builder;
    }

}
