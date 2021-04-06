package in.muchhala.here.domain;

import java.util.Set;

public record Project(String groupId, String artifactId, double version, Set<Dependency> dependencies) implements Node {
}
