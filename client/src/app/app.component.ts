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
  public messagesCount = [];
  public total = 0;
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
          const data = JSON.parse(message.body);
          const domain = this.messagesCount.find(item => item.value ===  data.value);

          if (domain) {
            domain.count = data.count;
          } else {
            this.messagesCount.push(data);
          }

          this.messagesCount.sort((a, b) => b.count - a.count);
          this.totalCount();
        }
      });
    });
    client.debug = () => {};
  }

  totalCount() {
     this.total = this.messagesCount
      .map(item => item.count)
      .reduce((total, currentValue) => total + currentValue);
  }

  ngOnDestroy() {
    this.newsSub.unsubscribe();
  }
}
