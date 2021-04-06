package in.muchhala.here.domain;

import java.util.Set;

public class Dependency implements Node {
    private final String groupId;
    private final String artifactId;
    private final double version;
    private Set<Dependency> downstream;

    public Dependency(String groupId, String artifactId, double version) {
        this(groupId, artifactId, version, null);
    }

    public Dependency(String groupId, String artifactId, double version, Set<Dependency> downstream) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.downstream = downstream;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public double getVersion() {
        return version;
    }

    public Set<Dependency> getDownstream() {
        return downstream;
    }

    public void setDownstream(Set<Dependency> downstream) {
        this.downstream = downstream;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dependency that = (Dependency) o;

        if (Double.compare(that.version, version) != 0) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (artifactId != null ? !artifactId.equals(that.artifactId) : that.artifactId != null) return false;
        return downstream != null ? downstream.equals(that.downstream) : that.downstream == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (artifactId != null ? artifactId.hashCode() : 0);
        temp = Double.doubleToLongBits(version);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (downstream != null ? downstream.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dependency{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version=" + version +
                '}';
    }


}
