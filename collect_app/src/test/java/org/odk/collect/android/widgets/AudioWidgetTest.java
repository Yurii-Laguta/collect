package org.odk.collect.android.widgets;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import net.bytebuddy.utility.RandomString;

import org.javarosa.core.model.data.StringData;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.odk.collect.android.BuildConfig;
import org.odk.collect.android.utilities.FileUtil;
import org.odk.collect.android.utilities.MediaUtil;
import org.odk.collect.android.widgets.base.FileWidgetTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author James Knight
 */
@Config(constants = BuildConfig.class)
@RunWith(RobolectricTestRunner.class)
public class AudioWidgetTest extends FileWidgetTest<AudioWidget> {

    @Mock
    Uri uri;

    @Mock
    MediaUtil mediaUtil;

    @Mock
    FileUtil fileUtil;

    private String destinationName = null;

    public AudioWidgetTest() {
        super(AudioWidget.class);
    }

    @NonNull
    @Override
    public AudioWidget createWidget() {
        AudioWidget audioWidget = new AudioWidget(RuntimeEnvironment.application, formEntryPrompt);

        audioWidget.setMediaUtil(mediaUtil);
        audioWidget.setFileUtil(fileUtil);

        return audioWidget;
    }

    @NonNull
    @Override
    public StringData getNextAnswer() {
        return new StringData(destinationName);
    }

    @Override
    public Object createBinaryData(StringData answerData) {
        return uri;
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();
        when(formEntryPrompt.isReadOnly()).thenReturn(false);

        when(mediaUtil.getPathFromUri(any(Context.class), any(Uri.class), any(String.class)))
                .thenReturn(String.format("%s.mp3", RandomString.make()));

        destinationName = RandomString.make();
        when(fileUtil.getRandomFilename()).thenReturn(destinationName);

        File firstFile = mock(File.class);

        when(fileUtil.getFileAtPath(String.format("/%s.mp3", destinationName)))
                .thenReturn(firstFile);

        when(firstFile.exists()).thenReturn(true);
        when(firstFile.getName()).thenReturn(destinationName);
    }
}