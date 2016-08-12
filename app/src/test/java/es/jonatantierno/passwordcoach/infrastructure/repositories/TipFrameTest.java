package es.jonatantierno.passwordcoach.infrastructure.repositories;


import android.view.ViewGroup;

import org.junit.Test;

import es.jonatantierno.passwordcoach.domain.model.tips.TipType;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;

public class TipFrameTest {
    @Test
    public void makeSureAllResultsAreMapped(){
        TipFrame tipFrame = new TipFrame(mock(ViewGroup.class));

        for (TipType type: TipType.values()) {
            assertNotNull(tipFrame.getId(type));
        }
    }

}