package com.randered.imdb.base;

import com.google.gson.Gson;
import com.randered.imdb.util.validation.ValidationResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public abstract class BaseMvcTest extends BaseTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected Gson gson;

    protected ValidationResponse performPostFileRequest(
            final String url, final String fileName, final MultipartFile file,
            final LinkedMultiValueMap<String, String> params, final ResultMatcher status) throws Exception {

        return buildResponse(multipart(url).file(fileName, file.getBytes()).params(params), status);
    }

    protected <T> ValidationResponse performPostRequest(
            final String url, final T object, final ResultMatcher status) throws Exception {

        return buildResponse(post(url).contentType(APPLICATION_JSON).content(gson.toJson(object)), status);
    }

    protected ValidationResponse performPostRequest(
            final String url, final ResultMatcher status,
            final LinkedMultiValueMap<String, String> params) throws Exception {

        return buildResponse(post(url).params(params), status);
    }

    protected <T> ValidationResponse performPutRequest(
            final String url, final T object, ResultMatcher status) throws Exception {

        return buildResponse(put(url).contentType(APPLICATION_JSON).content(gson.toJson(object)), status);
    }

    protected ValidationResponse performDeleteRequest(
            final String url, ResultMatcher status, LinkedMultiValueMap<String, String> params) throws Exception {

        return buildResponse(delete(url).params(params), status);
    }

    protected ValidationResponse performDeleteRequest(
            final String url, final ResultMatcher status, final Object... vars) throws Exception {

        return buildResponse(delete(url, vars), status);
    }

    private ValidationResponse buildResponse(RequestBuilder request, ResultMatcher expect) throws Exception {
        return gson.fromJson(
                mockMvc.perform(request)
                        .andExpect(expect)
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                ValidationResponse.class);
    }
}
