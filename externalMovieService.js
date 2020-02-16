const http = require('http');

const PORT = 3000;

const server = http.createServer((request, response) => {
    const headers = request.headers
    const correlationId = headers['correlationid']
    console.log(headers)
    console.log(`CorrelationId: ${correlationId}`)

    if(request.url === '/movies' && request.method === 'GET') {
        response.writeHead(200, {'content-type': 'application/json'});
        const jsonResponse = [{"title":"movie A","description":"description for movie A from NODEJS APP"},{"title":"movie B","description":"description for movie B from NODEJS APP"}];
        response.end(JSON.stringify(jsonResponse))
    }
    if(request.url === '/movies/bad-request' && request.method === 'GET') {
        response.writeHead(400, {'content-type': 'application/json'});
        let jsonResponse = {"status": "400", "message": "You got a 400 error. Totally your fault"};
        response.end(JSON.stringify(jsonResponse))
    }
    else {
        response.writeHead(404);
        response.end();
    }
});

server.listen(PORT, () => console.log(`External Movies Service running on http://localhost:${PORT}`));
