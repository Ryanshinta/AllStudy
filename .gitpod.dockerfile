RUN bash -c "sdk install java 17.0.3-ms && \
    sdk default java 17.0.3-ms"

FROM gitpod/workspace-full
FROM gitpod/workspace-mysql

