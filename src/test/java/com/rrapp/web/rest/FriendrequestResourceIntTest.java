package com.rrapp.web.rest;

import com.rrapp.Application;
import com.rrapp.domain.Friendrequest;
import com.rrapp.repository.FriendrequestRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the FriendrequestResource REST controller.
 *
 * @see FriendrequestResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FriendrequestResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_SENT_DATE_AND_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SENT_DATE_AND_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SENT_DATE_AND_TIME_STR = dateTimeFormatter.format(DEFAULT_SENT_DATE_AND_TIME);

    private static final Boolean DEFAULT_IS_ACCEPTED = false;
    private static final Boolean UPDATED_IS_ACCEPTED = true;

    private static final ZonedDateTime DEFAULT_ACCEPTED_DATE_AND_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ACCEPTED_DATE_AND_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ACCEPTED_DATE_AND_TIME_STR = dateTimeFormatter.format(DEFAULT_ACCEPTED_DATE_AND_TIME);

    @Inject
    private FriendrequestRepository friendrequestRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFriendrequestMockMvc;

    private Friendrequest friendrequest;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FriendrequestResource friendrequestResource = new FriendrequestResource();
        ReflectionTestUtils.setField(friendrequestResource, "friendrequestRepository", friendrequestRepository);
        this.restFriendrequestMockMvc = MockMvcBuilders.standaloneSetup(friendrequestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        friendrequest = new Friendrequest();
        friendrequest.setSentDateAndTime(DEFAULT_SENT_DATE_AND_TIME);
        friendrequest.setIsAccepted(DEFAULT_IS_ACCEPTED);
        friendrequest.setAcceptedDateAndTime(DEFAULT_ACCEPTED_DATE_AND_TIME);
    }

    @Test
    @Transactional
    public void createFriendrequest() throws Exception {
        int databaseSizeBeforeCreate = friendrequestRepository.findAll().size();

        // Create the Friendrequest

        restFriendrequestMockMvc.perform(post("/api/friendrequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(friendrequest)))
                .andExpect(status().isCreated());

        // Validate the Friendrequest in the database
        List<Friendrequest> friendrequests = friendrequestRepository.findAll();
        assertThat(friendrequests).hasSize(databaseSizeBeforeCreate + 1);
        Friendrequest testFriendrequest = friendrequests.get(friendrequests.size() - 1);
        assertThat(testFriendrequest.getSentDateAndTime()).isEqualTo(DEFAULT_SENT_DATE_AND_TIME);
        assertThat(testFriendrequest.getIsAccepted()).isEqualTo(DEFAULT_IS_ACCEPTED);
        assertThat(testFriendrequest.getAcceptedDateAndTime()).isEqualTo(DEFAULT_ACCEPTED_DATE_AND_TIME);
    }

    @Test
    @Transactional
    public void checkSentDateAndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = friendrequestRepository.findAll().size();
        // set the field null
        friendrequest.setSentDateAndTime(null);

        // Create the Friendrequest, which fails.

        restFriendrequestMockMvc.perform(post("/api/friendrequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(friendrequest)))
                .andExpect(status().isBadRequest());

        List<Friendrequest> friendrequests = friendrequestRepository.findAll();
        assertThat(friendrequests).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsAcceptedIsRequired() throws Exception {
        int databaseSizeBeforeTest = friendrequestRepository.findAll().size();
        // set the field null
        friendrequest.setIsAccepted(null);

        // Create the Friendrequest, which fails.

        restFriendrequestMockMvc.perform(post("/api/friendrequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(friendrequest)))
                .andExpect(status().isBadRequest());

        List<Friendrequest> friendrequests = friendrequestRepository.findAll();
        assertThat(friendrequests).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFriendrequests() throws Exception {
        // Initialize the database
        friendrequestRepository.saveAndFlush(friendrequest);

        // Get all the friendrequests
        restFriendrequestMockMvc.perform(get("/api/friendrequests?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(friendrequest.getId().intValue())))
                .andExpect(jsonPath("$.[*].sentDateAndTime").value(hasItem(DEFAULT_SENT_DATE_AND_TIME_STR)))
                .andExpect(jsonPath("$.[*].isAccepted").value(hasItem(DEFAULT_IS_ACCEPTED.booleanValue())))
                .andExpect(jsonPath("$.[*].acceptedDateAndTime").value(hasItem(DEFAULT_ACCEPTED_DATE_AND_TIME_STR)));
    }

    @Test
    @Transactional
    public void getFriendrequest() throws Exception {
        // Initialize the database
        friendrequestRepository.saveAndFlush(friendrequest);

        // Get the friendrequest
        restFriendrequestMockMvc.perform(get("/api/friendrequests/{id}", friendrequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(friendrequest.getId().intValue()))
            .andExpect(jsonPath("$.sentDateAndTime").value(DEFAULT_SENT_DATE_AND_TIME_STR))
            .andExpect(jsonPath("$.isAccepted").value(DEFAULT_IS_ACCEPTED.booleanValue()))
            .andExpect(jsonPath("$.acceptedDateAndTime").value(DEFAULT_ACCEPTED_DATE_AND_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingFriendrequest() throws Exception {
        // Get the friendrequest
        restFriendrequestMockMvc.perform(get("/api/friendrequests/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFriendrequest() throws Exception {
        // Initialize the database
        friendrequestRepository.saveAndFlush(friendrequest);

		int databaseSizeBeforeUpdate = friendrequestRepository.findAll().size();

        // Update the friendrequest
        friendrequest.setSentDateAndTime(UPDATED_SENT_DATE_AND_TIME);
        friendrequest.setIsAccepted(UPDATED_IS_ACCEPTED);
        friendrequest.setAcceptedDateAndTime(UPDATED_ACCEPTED_DATE_AND_TIME);

        restFriendrequestMockMvc.perform(put("/api/friendrequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(friendrequest)))
                .andExpect(status().isOk());

        // Validate the Friendrequest in the database
        List<Friendrequest> friendrequests = friendrequestRepository.findAll();
        assertThat(friendrequests).hasSize(databaseSizeBeforeUpdate);
        Friendrequest testFriendrequest = friendrequests.get(friendrequests.size() - 1);
        assertThat(testFriendrequest.getSentDateAndTime()).isEqualTo(UPDATED_SENT_DATE_AND_TIME);
        assertThat(testFriendrequest.getIsAccepted()).isEqualTo(UPDATED_IS_ACCEPTED);
        assertThat(testFriendrequest.getAcceptedDateAndTime()).isEqualTo(UPDATED_ACCEPTED_DATE_AND_TIME);
    }

    @Test
    @Transactional
    public void deleteFriendrequest() throws Exception {
        // Initialize the database
        friendrequestRepository.saveAndFlush(friendrequest);

		int databaseSizeBeforeDelete = friendrequestRepository.findAll().size();

        // Get the friendrequest
        restFriendrequestMockMvc.perform(delete("/api/friendrequests/{id}", friendrequest.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Friendrequest> friendrequests = friendrequestRepository.findAll();
        assertThat(friendrequests).hasSize(databaseSizeBeforeDelete - 1);
    }
}
