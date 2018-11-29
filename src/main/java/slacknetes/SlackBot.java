package slacknetes;

import io.kubernetes.client.ApiException;
import io.kubernetes.client.models.V1JobList;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SlackBot extends Bot {

    private final KubernetesService kubernetesService;
    private final String slackToken;

    public SlackBot(KubernetesService kubernetesService, @Value("${slackBotToken}") String slackToken) {
        this.kubernetesService = kubernetesService;
        this.slackToken = slackToken;
    }

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceive(WebSocketSession session, Event event) throws ApiException {
        if (event.getText().toLowerCase().contains("jobs")) {
            V1JobList jobs = kubernetesService.listNamespacedJob();
            String version = jobs.getApiVersion();
            reply(session, event, new Message(version));
            return;
        }
        if (event.getText().toLowerCase().contains("ciao")) {
            V1JobList jobs = kubernetesService.listNamespacedJob();
            String version = "cia bella";
            reply(session, event, new Message(version));
            return;
        }
        reply(session, event, new Message("Hi, the following keywords are supported\njobs\nciao"));
    }

}
