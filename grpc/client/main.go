package main

import (
	"context"
	"fmt"
	"google.golang.org/grpc"
	"local/pb/person"
	"log"
	"sync"
	"time"
)

func main() {
	// 拨号信息
	dial, err := grpc.Dial("127.0.0.1:8888", grpc.WithInsecure())
	CheckError(err)
	// 建立连接
	client := person.NewPersonServiceClient(dial)

	// 1️⃣ 普通请求
	res, err := client.Test(context.Background(), &person.PersonReq{Msg: "张三<Test>"})
	CheckError(err)
	fmt.Println(res)

	// 2️⃣ 客户端流
	streamClient, err := client.TestStreamClient(context.Background())
	CheckError(err)
	for i := 0; i < 10; i++ {
		err := streamClient.Send(&person.PersonReq{Msg: fmt.Sprintf("张三<TestStreamClient>%d", i)})
		CheckError(err)
		time.Sleep(500 * time.Millisecond)
	}
	res, err = streamClient.CloseAndRecv()
	CheckError(err)
	fmt.Println(res)

	// 3️⃣ 服务端流
	streamServer, err := client.TestStreamServer(context.Background(), &person.PersonReq{Msg: "张三<TestStreamServer>"})
	CheckError(err)
	for {
		res, err := streamServer.Recv()
		if err != nil {
			fmt.Println(err)
			break
		}
		fmt.Println(res)
	}

	// 4️⃣ 入流出流
	streamIO, err := client.TestStreamIO(context.Background())
	CheckError(err)
	waitGroup := sync.WaitGroup{}
	waitGroup.Add(2)
	go func() {
		for {
			res, err := streamIO.Recv()
			if err != nil {
				waitGroup.Done()
				break
			}
			fmt.Println(res)
		}
	}()
	go func() {
		for i := 0; i < 10; i++ {
			err := streamIO.Send(&person.PersonReq{Msg: fmt.Sprintf("张三<TestStreamIO>%d", i)})
			if err != nil {
				break
			}
			time.Sleep(500 * time.Millisecond)
		}
		_ = streamIO.Send(&person.PersonReq{Msg: "close"})
		waitGroup.Done()
	}()
	waitGroup.Wait()
}

func CheckError(err error) {
	if err != nil {
		log.Fatal(err)
	}
}
