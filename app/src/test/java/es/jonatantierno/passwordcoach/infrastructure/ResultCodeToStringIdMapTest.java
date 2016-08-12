package es.jonatantierno.passwordcoach.infrastructure;

import org.junit.Test;

import es.jonatantierno.passwordcoach.domain.model.rules.ResultCode;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResultCodeToStringIdMapTest {
    @Test
    public void makeSureAllResultsAreMapped(){
        ResultCodeToStringIdMap map = new ResultCodeToStringIdMap();

        for (ResultCode code: ResultCode.values()) {
            assertNotNull(map.get(code));
        }
    }

}