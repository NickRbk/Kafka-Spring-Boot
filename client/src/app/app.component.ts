import {Component, OnDestroy} from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {Subscription} from '../../node_modules/rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnDestroy {

  private serverUrl = `http://localhost:8081/socket`;
  public title = 'WebSocket connection STATUS: OFFLINE... Please reload page';
  public messagesCount = 0;
  private newsSub: Subscription;

  constructor() {
    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
    const ws = new SockJS(this.serverUrl);
    const client = Stomp.over(ws);
    client.connect({}, () => {
      this.title = 'WebSocket connection STATUS: OK';
      this.newsSub = client.subscribe('/news', (message) => {
        if (message.body) {
          this.messagesCount++;
        }
      });
    });
    client.debug = () => {};
  }

  ngOnDestroy() {
    this.newsSub.unsubscribe();
  }
}
