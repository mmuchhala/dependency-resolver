package in.muchhala.here.resolver;

import in.muchhala.here.domain.Dependency;
import in.muchhala.here.domain.Project;

import java.util.*;

public class ProjectDependencyResolver implements DependencyResolver<Project> {

    private final Map<String, Dependency> allDependencies;
    private final DependencyResolver<Dependency> downstreamDependencyResolver;

    public ProjectDependencyResolver(DependencyResolver<Dependency> downstreamDependencyResolver) {
        this.allDependencies = new HashMap<>();
        this.downstreamDependencyResolver = downstreamDependencyResolver;
    }

    @Override
    public void resolve(Project project) {
        for(Dependency d: project.dependencies()) {
            downstreamDependencyResolver.resolve(d);
        }
    }

    @Override
    public Set<Dependency> getAllDependencies(Project project) {
        if(Objects.nonNull(project.dependencies())) {
            for (Dependency d : project.dependencies()) {
                DependencyResolverHelper.addDependency(allDependencies, d);
                if (Objects.nonNull(d.getDownstream())) {
                    for (Dependency downstream : d.getDownstream()) {
                        Set<Dependency> allDownstreamDependencies = downstreamDependencyResolver.getAllDependencies(downstream);
                        allDownstreamDependencies.forEach(dd -> DependencyResolverHelper.addDependency(allDependencies, dd));
                    }
                }
            }
        }

        return new HashSet<>(allDependencies.values());
    }
}
