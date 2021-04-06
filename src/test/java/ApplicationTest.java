import in.muchhala.here.domain.Dependency;
import in.muchhala.here.domain.Project;
import in.muchhala.here.fetcher.DownstreamDependencyFetcher;
import in.muchhala.here.resolver.DependencyResolver;
import in.muchhala.here.resolver.DownstreamDependencyResolver;
import in.muchhala.here.resolver.ProjectDependencyResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class ApplicationTest {

    DownstreamDependencyFetcher downstreamDependencyFetcher;
    DependencyResolver<Dependency> downstreamDependencyResolver;
    DependencyResolver<Project> dependencyResolver;

    @BeforeEach
    public void setup() {
        downstreamDependencyFetcher = Mockito.mock(DownstreamDependencyFetcher.class);
        downstreamDependencyResolver = new DownstreamDependencyResolver(downstreamDependencyFetcher);
        dependencyResolver = new ProjectDependencyResolver(downstreamDependencyResolver);
    }


    @Test
    public void shouldGetAllDependencies() {
        //given
        Dependency a = new Dependency("G1", "A", 1.0);
        Dependency b = new Dependency("G1", "B", 1.0);
        Set<Dependency> projectDependencies = new HashSet<>();
        projectDependencies.add(a);
        projectDependencies.add(b);

        Dependency i1 = new Dependency("G2", "I", 1.0);
        Dependency j = new Dependency("G3", "J", 1.1);
        Set<Dependency> dependenciesOfA = new HashSet<>();
        dependenciesOfA.add(i1);
        dependenciesOfA.add(j);

        Dependency i2 = new Dependency("G2", "I", 2.0);
        Dependency k = new Dependency("G4", "K", 1.0);

        Set<Dependency> dependenciesOfB = new HashSet<>();
        dependenciesOfB.add(i2);
        dependenciesOfB.add(k);

        Project project = new Project("in.muchhala.here", "dependency-resolver", 1.0, projectDependencies);

        Mockito.when(downstreamDependencyFetcher.getDownstreamDependency(a)).thenReturn(Optional.of(dependenciesOfA));
        Mockito.when(downstreamDependencyFetcher.getDownstreamDependency(b)).thenReturn(Optional.of(dependenciesOfB));
        Mockito.when(downstreamDependencyFetcher.getDownstreamDependency(i1)).thenReturn(Optional.empty());
        Mockito.when(downstreamDependencyFetcher.getDownstreamDependency(i2)).thenReturn(Optional.empty());
        Mockito.when(downstreamDependencyFetcher.getDownstreamDependency(j)).thenReturn(Optional.empty());
        Mockito.when(downstreamDependencyFetcher.getDownstreamDependency(k)).thenReturn(Optional.empty());

        Set<Dependency> expectedAllDependencies = new HashSet<>();
        expectedAllDependencies.add(a);
        expectedAllDependencies.add(b);
        expectedAllDependencies.add(i2);
        expectedAllDependencies.add(j);
        expectedAllDependencies.add(k);

        //when
        dependencyResolver.resolve(project);
        Set<Dependency> actualAllDependencies = dependencyResolver.getAllDependencies(project);

        //then
        System.out.println("Resolved dependencies: " + actualAllDependencies.stream().map(Dependency::toString).collect(Collectors.joining(", ")));
        Assertions.assertEquals(expectedAllDependencies.size(), actualAllDependencies.size());
        Assertions.assertTrue(actualAllDependencies.containsAll(expectedAllDependencies));
    }


}
