import { Component } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  private serverUrl = 'http://localhost:8081/socket';
  public title = 'WebSockets chat';
  private stompClient;

  constructor() {
    this.initializeWebSocketConnection();
  }
  initializeWebSocketConnection() {
    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;

    this.stompClient.connect({}, () => {
      that.stompClient.subscribe('news', (message) => {
        if (message.body) {
          console.log(message.body);
        }
      });
    });
  }
}
