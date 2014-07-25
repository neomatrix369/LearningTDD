package main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    private static final UserId USER_ID = new UserId(5);
    private static final Message MESSAGE = new Message(10, "any message");

    @Mock private NewsFeedReader newsFeedReader;

    private Application application;

    @Before
    public void setup() {
        application = new Application(newsFeedReader);
    }

    @Test
    public void delegatesGetQueryToNewsFeedReader() {
        List<Message> expectedNewsFeed = Arrays.asList(MESSAGE);

        when(newsFeedReader.getNewsFeed(USER_ID)).thenReturn(expectedNewsFeed);

        List<Message> actualNewsFeed = application.getNewsFeed(USER_ID);

        assertThat(actualNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

    @Test
    public void delegatesPostQueryToNewsFeedReader() {
        application.post(USER_ID, MESSAGE);

        verify(newsFeedReader, times(1)).post(USER_ID, MESSAGE);
    }

}