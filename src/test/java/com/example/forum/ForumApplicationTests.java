package com.example.forum;

import com.example.forum.command.InsertMessage;
import com.example.forum.dto.Message;
import com.example.forum.dto.Messageboard;
import com.example.forum.testdata.ForumBuilder;
import com.example.forum.testdata.TestdataBuilder;
import com.example.forum.testdata.TestdataDao;
import com.example.forum.util.CollectionUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ForumApplicationTests {
	@Autowired private ForumService forumService;
	@Autowired private TestdataDao testdataDao;
	@Autowired private TestRestTemplate testRestTemplate;

	@AfterEach
	public void afterEach() {
		testdataDao.cleanTestdata();
	}

	@Test
	void can_list_messageboards_with_message_counts() {
		// Arrange: we have a forum with messages
		var messageCount = 3;
		ForumBuilder forumBuilder = testdataDao.insertForum(new ForumBuilder().withMessages(messageCount));

		// Act: when we fetch the messageboards
		var response = testRestTemplate.getForEntity("/messageboards", Messageboard[].class);

		// Assert: the initiated messageboard is found and has correct message count
		Messageboard messageboard = CollectionUtil.getExactlyOne(response.getBody(),
				mb -> forumBuilder.messageboardId.equals(mb.messageboardId()));

		assertEquals(messageCount, messageboard.messageCount());
	}

	@Test
	void can_add_message_to_a_messageboard() {
		// Arrange: we have a forum without messages
		ForumBuilder forumBuilder = testdataDao.insertForum(new ForumBuilder().withMessages(0));
		InsertMessage insertMessage = TestdataBuilder.insertMessage().build();

		// Act: when we add a message
		var response =
				testRestTemplate.postForEntity("/messageboards/{messageboardId}/messages",
						insertMessage, Message.class, forumBuilder.messageboardId);

		// Assert: the message is added and returned
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(insertMessage.text(), requireNonNull(response.getBody()).text());

		List<Message> messages = forumService.getMessages(forumBuilder.messageboardId);
		assertEquals(1, messages.size());
		assertEquals(insertMessage.text(), messages.get(0).text());
	}

	@Test
	void can_get_messages_from_a_messageboard() {
		// Arrange: we have a forum with messages
		ForumBuilder forumBuilder = testdataDao.insertForum(new ForumBuilder()
				.withMessage("My message 1")
				.withMessage("My message 2")
		);

		// Act: when we fetch the messages
		var response =
				testRestTemplate.getForEntity("/messageboards/{messageboardId}/messages",
						Message[].class, forumBuilder.messageboardId);

		// Assert: the messages are found
		var messages = Arrays.stream(requireNonNull(response.getBody())).toList();
		assertEquals(2, messages.size());
		assertThat(messages.stream().anyMatch(msg -> msg.text().equals("My message 1"))).isTrue();
		assertThat(messages.stream().anyMatch(msg -> msg.text().equals("My message 2"))).isTrue();
	}

	@Test
	void messages_with_bad_words_are_rejected() {
		// Arrange: we have a forum without messages
		ForumBuilder forumBuilder = testdataDao.insertForum(new ForumBuilder().withMessages(0));
		InsertMessage insertMessage = TestdataBuilder.insertMessage().text("adidas").build();

		// Act: when we add a message
		var response =
				testRestTemplate.postForEntity("/messageboards/{messageboardId}/messages",
						insertMessage, Message.class, forumBuilder.messageboardId);

		// Assert: the message is not added
		assertEquals(0, forumService.getMessages(forumBuilder.messageboardId).size());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void too_short_messages_are_rejected() {
		// Arrange: we have a forum without messages
		ForumBuilder forumBuilder = testdataDao.insertForum(new ForumBuilder().withMessages(0));
		// And an invalid message with too short text
		Map<String, Object> insertMessage = Map.of("userId", 1, "text", "a");

		// Act: when we add the invalid message
		var response =
				testRestTemplate.postForEntity("/messageboards/{messageboardId}/messages",
						insertMessage, Map.class, forumBuilder.messageboardId);

		// Assert: the message is not added and an error response is returned
		assertEquals(0, forumService.getMessages(forumBuilder.messageboardId).size());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		var responseBody = requireNonNull(response.getBody());
		assertEquals("InvalidJsonException", responseBody.get("error"));
		assertEquals("Input value too short", responseBody.get("message"));
	}
}
