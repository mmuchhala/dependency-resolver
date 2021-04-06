package in.muchhala.here.resolver;

import in.muchhala.here.domain.Dependency;

import java.util.Map;

public class DependencyResolverHelper {

    public static void addDependency(Map<String, Dependency> allDependencies, Dependency newDependency) {
        String key = newDependency.getGroupId() + newDependency.getArtifactId();
        if(allDependencies.containsKey(key)) {
            Dependency existingDependency = allDependencies.get(key);
            if(newDependency.getVersion() > existingDependency.getVersion()) {
                allDependencies.put(key, newDependency);
            }
        } else {
            allDependencies.put(key, newDependency);
        }

    }
}
