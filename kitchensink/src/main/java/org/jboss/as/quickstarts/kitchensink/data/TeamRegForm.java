package org.jboss.as.quickstarts.kitchensink.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamRegForm implements Serializable {
    private long teamId;
    private long contestId;
}
