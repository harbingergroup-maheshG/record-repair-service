package com.myco.record_repair.elastic.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myco.record_repair.model.UserModel;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

@Repository
public class ElasticSearchQuery {

	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchQuery.class);

	@Autowired
	private ElasticsearchClient elasticsearchClient;

	private static final String USER_INFORMATION_ELASTICSEARCH = "userinformation_elasticsearch";

	public String createOrUpdateDocument(UserModel user) throws Exception {
		LOGGER.info(">>>> createOrUpdateDocument() , employee : {}", user);
		IndexResponse response = elasticsearchClient
				.index(i -> i.index(USER_INFORMATION_ELASTICSEARCH).id(user.getId()).document(user));
		if (response.result().name().equals("Created")) {
			LOGGER.info("<<<< createOrUpdateDocument() <<<< Document has been successfully created.");
			return new StringBuilder("Document has been successfully created.").toString();
		} else if (response.result().name().equals("Updated")) {
			LOGGER.info("<<<< createOrUpdateDocument() <<<< Document has been successfully updated.");
			return new StringBuilder("Document has been successfully updated.").toString();
		}
		LOGGER.error(">>>> error on createOrUpdateDocument()");
		return new StringBuilder("Error while performing the operation.").toString();
	}


    public String deleteDocumentById(String id) throws IOException {

        DeleteRequest request = DeleteRequest.of(d -> d.index(USER_INFORMATION_ELASTICSEARCH).id(id));

        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("Product with id " + deleteResponse.id() + " has been deleted.").toString();
        }
        LOGGER.info("User not found");
        return new StringBuilder("User with id " + deleteResponse.id() + " does not exist.").toString();

    }

	public List<UserModel> searchAllDocuments() throws IOException {
		LOGGER.info(">>>> searchAllDocuments");
		SearchRequest searchRequest = SearchRequest.of(s -> s.index(USER_INFORMATION_ELASTICSEARCH));
		SearchResponse searchResponse = elasticsearchClient.search(searchRequest, UserModel.class);
		List<Hit> hits = searchResponse.hits().hits();
		List<UserModel> users = new ArrayList<>();
		for (Hit object : hits) {
			LOGGER.info(">>>> User : {}", ((UserModel) object.source()));
			users.add((UserModel) object.source());

		}
		return users;
	}

	public UserModel getDocumentById(String id) throws IOException {
		LOGGER.info(">>>> getDocumentByTaskId : User id :  {}", id);
		UserModel user = null;
		GetResponse<UserModel> response = elasticsearchClient.get(g -> g.index(USER_INFORMATION_ELASTICSEARCH).id(id),
				UserModel.class);

		if (response.found()) {
			user = response.source();
			LOGGER.info("<<<< User : {}", user);
		} else {
			LOGGER.info("<<<< User not found");
		}

		return user;
	}
}
