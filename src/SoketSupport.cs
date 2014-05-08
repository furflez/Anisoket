using System;
using System.IO;
using System.Net;
using System.Net.Sockets;

namespace Anisoket
{
    public class SoketSupport
    {
        private As _typeConnection;

        private NetworkStream _ns;

        private StreamWriter _writer;
        private StreamReader _reader;

        private TcpListener _server;
        private TcpClient _client;
        private Socket _socket;

        private string _ip;
        private int _port;
        /// <summary>
        /// Configure the soket as Client or Server, with Server's Ip and Port.
        /// </summary>
        /// <param name="typeConnection"></param>
        /// <param name="ip"></param>
        /// <param name="port"></param>
        public void Configure(As typeConnection, string ip, string port)
        {
            _ip = ip;
            _port = Convert.ToInt32(port);

            _typeConnection = typeConnection;

            switch (typeConnection)
            {
                case As.Server:

                    _server = new TcpListener(IPAddress.Parse(_ip), _port);
                    _server.Start();

                    break;
                case As.Client:

                    _client = new TcpClient();
                    break;
            }

        }
        public void SendData(string data)
        {
            switch (_typeConnection)
            {
                case As.Server:

                    _writer.WriteLine(data);
                    _writer.Flush();
                    _socket.Close();

                    break;
                case As.Client:

                    _client = new TcpClient(_ip, _port);
                    _ns = _client.GetStream();

                    _writer = new StreamWriter(_ns);
                    _reader = new StreamReader(_ns);

                    if (_client.Connected)

                        _writer.WriteLine(data);
                    _writer.Flush();

                    break;
            }

        }

        public string ReceiveData()
        {
            string data = null;

            switch (_typeConnection)
            {
                case As.Server:
                    _socket = _server.AcceptSocket();

                    _ns = new NetworkStream(_socket);

                    _writer = new StreamWriter(_ns);
                    _reader = new StreamReader(_ns);

                    return _reader.ReadLine();

                case As.Client:

                    data = _reader.ReadLine();
                    _client.Close();
                    break;
            }
            return data;
        }


        public As GetTypeConnection()
        {
            return _typeConnection;
        }
        /// <summary>
        /// Return this machine IP on current gateway
        /// </summary>
        /// <returns></returns>
        public string[] GetLocalIp()
        {
            string nome = Dns.GetHostName();

            IPAddress[] ip = Dns.GetHostAddresses(nome);

            var response = new string[ip.Length+1];
            response[0] = "127.0.0.1";
            for (int index = 0; index < ip.Length; index++)
            {
                response[index+1] = ip[index].ToString();
            }
            return response;

        }

        public void ForceClose()
        {
            switch (_typeConnection)
            {
                case As.Server:

                    _socket.Close();
                    break;
                case As.Client:
                    

                    _client.Close();
                    break;
            }
        }
    }

    public enum As
    {
        Client, Server
    }

}
