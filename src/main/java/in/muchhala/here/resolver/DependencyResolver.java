package in.muchhala.here.resolver;

import in.muchhala.here.domain.Dependency;
import in.muchhala.here.domain.Node;

import java.util.Set;

public interface DependencyResolver<T extends Node> {

    void resolve(T t);

    Set<Dependency> getAllDependencies(T t);
}
