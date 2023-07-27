
# 백엔드


### Endpoint

```agsl

# 현재 로그인 유저 정보
/api/v1/users      - GET
 
# 프로젝트 api
/api/projects      - GET, POST
/api/projects/{id} - GET, POST, PUT, DELETE 
/api/projects      - GET, POST
/api/projects/{id} - GET, POST, PUT, DELETE 
/api/projects/favorite - POST, DELETE


# 아티스트 api
/api/artists      - GET, POST
/api/artists/{id} - GET, POST, PUT, DELETE 
/api/artists      - GET, POST
/api/artists/{id} - GET, POST, PUT, DELETE 
/api/artists/favorite - POST, DELETE

# 아티스트 스와이프 api
/api/artists/swipe/received - GET
/api/artists/swipe/matched - GET
/api/artists/swipe/like - POST
/api/artists/swipe/reject - POST



# swagger-ui
/swagger-ui.html

```