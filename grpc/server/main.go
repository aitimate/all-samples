package main

import (
	"context"
	"fmt"
	"google.golang.org/grpc"
	"local/pb/person"
	"log"
	"net"
)

func main() {
	// 注册接口
	server := grpc.NewServer()
	person.RegisterPersonServiceServer(server, &PersonServiceServer{})
	// 监听实例
	listener, err := net.Listen("tcp", ":8888")
	CheckError(err)
	defer listener.Close()
	// 建立监听
	err = server.Serve(listener)
}

func CheckError(err error) {
	if err != nil {
		log.Fatal(err)
	}
}

// PersonServiceServer 1️⃣ 2️⃣ 3️⃣ 4️⃣ 接口实现
type PersonServiceServer struct {
	person.UnimplementedPersonServiceServer
}

// Test 1️⃣ 普通请求接口
func (*PersonServiceServer) Test(ctx context.Context, req *person.PersonReq) (*person.PersonRes, error) {
	return &person.PersonRes{Msg: fmt.Sprintf("服务端<Test>: 已收到来自%s的信息", req.Msg)}, nil
}

// TestStreamClient 2️⃣ 客户端流接口
func (*PersonServiceServer) TestStreamClient(stream person.PersonService_TestStreamClientServer) error {
	// 服务端有对流主动关闭的权利
	for {
		req, err := stream.Recv()
		if err != nil {
			err := stream.SendAndClose(&person.PersonRes{Msg: "服务端<TestStreamClient>: OK!"})
			return err
		}
		fmt.Println(req)
	}
}

// TestStreamServer 3️⃣ 服务端流接口
func (*PersonServiceServer) TestStreamServer(req *person.PersonReq, stream person.PersonService_TestStreamServerServer) error {
	fmt.Println(req)
	for i := 0; i < 10; i++ {
		err := stream.Send(&person.PersonRes{Msg: fmt.Sprintf("服务端<TestStreamServer>%d", i)})
		CheckError(err)
	}
	return nil
}

// TestStreamIO 4️⃣ 入流出流接口
func (*PersonServiceServer) TestStreamIO(streamIO person.PersonService_TestStreamIOServer) error {
	chanel := make(chan string)
	go func() {
		for {
			req, err := streamIO.Recv()
			if err != nil {
				break
			}
			chanel <- req.Msg
		}
	}()
	for {
		msg := <-chanel
		fmt.Println(msg)
		err := streamIO.Send(&person.PersonRes{Msg: fmt.Sprintf("服务端<TestStreamIO>: %s", msg)})
		if err != nil || msg == "close" {
			break
		}
	}
	return nil
}
