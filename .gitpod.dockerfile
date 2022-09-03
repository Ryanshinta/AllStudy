FROM gitpod/workspace-full

RUN sudo apt update \
    && sudo apt install -y \


FROM gitpod/workspace-mysql