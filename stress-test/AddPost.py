from locust import HttpUser, task, between
import random

class AddPosts(HttpUser):
    wait_time = between(1, 2)
    
    def on_start(self):
        self.client.post("/users/sign-in", json={"userId": "test","password":"123"})
        
    @task
    def add_post(self):
        self.client.post("/posts", json = {
            "title": "테스트 게시글" + str(random.randint(1,10000)),
            "content": "테스트 컨텐츠" + str(random.randint(1,10000)),
            "categoryId": random.randint(1,10),
            "isAdmin":False,
            "files":[],
            "tags":[]
        })
