package slacknetes;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.BatchV1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1JobList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KubernetesService {
    private final ApiClient apiClient;
    private final CoreV1Api coreV1Api;
    private final BatchV1Api batchV1Api;

    @Value("${namespace}") private String namespace;

    public KubernetesService(ApiClient apiClient, CoreV1Api coreV1Api, BatchV1Api batchV1Api) {
        this.apiClient = apiClient;
        this.coreV1Api = coreV1Api;
        this.batchV1Api = batchV1Api;
    }

    public V1JobList listNamespacedJob() throws ApiException {
        V1JobList jobs = batchV1Api.listNamespacedJob(namespace, null, null, null, null, null,
                null, null, null, null);
        return jobs;
    }
}
