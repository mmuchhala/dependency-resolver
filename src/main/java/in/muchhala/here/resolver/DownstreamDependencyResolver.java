package in.muchhala.here.resolver;

import in.muchhala.here.domain.Dependency;
import in.muchhala.here.fetcher.DownstreamDependencyFetcher;

import java.util.*;

public class DownstreamDependencyResolver implements DependencyResolver<Dependency> {

    private final Map<String, Dependency> allDependencies;
    private final DownstreamDependencyFetcher fetcher;

    public DownstreamDependencyResolver(DownstreamDependencyFetcher fetcher) {
        this.allDependencies = new HashMap<>();
        this.fetcher = fetcher;
    }

    @Override
    public void resolve(Dependency dependency) {
        if(Objects.isNull(dependency.getDownstream())) {
            Optional<Set<Dependency>> downstream = fetcher.getDownstreamDependency(dependency);
            if(downstream.isPresent()) {
                dependency.setDownstream(downstream.get());

                for (Dependency d : dependency.getDownstream()) {
                    resolve(d);
                }
            }
        }
    }

    @Override
    public Set<Dependency> getAllDependencies(Dependency dependency) {
        DependencyResolverHelper.addDependency(allDependencies, dependency);
        if(Objects.nonNull(dependency.getDownstream())) {
            for (Dependency d : dependency.getDownstream()) {
                DependencyResolverHelper.addDependency(allDependencies, d);
                if (Objects.nonNull(d.getDownstream())) {
                    for (Dependency downstream : d.getDownstream()) {
                        Set<Dependency> allDownstreamDependencies = getAllDependencies(downstream);
                        allDownstreamDependencies.forEach(dd -> DependencyResolverHelper.addDependency(allDependencies, dd));
                    }
                }
            }
        }

        return new HashSet<>(allDependencies.values());
    }
}
