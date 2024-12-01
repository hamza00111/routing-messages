import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Message } from '../models/message.model';
import { API_ENDPOINTS } from '../../core/constants/api.constants';
import {PaginatedResponse} from "../../shared/models/paginated-response.model";

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  constructor(private http: HttpClient) {}

  getMessages(page: number = 0, size: number = 10): Observable<PaginatedResponse<Message>> {
    const params = new HttpParams()
      .set('offset', page.toString())
      .set('limit', size.toString());

    return this.http.get<PaginatedResponse<Message>>(API_ENDPOINTS.MESSAGES, { params }).pipe(
      map(response => ({
        ...response,
        content: response.content.map(msg => ({
          ...msg,
          timestamp: new Date(msg.timestamp)
        }))
      })),
      catchError(this.handleError)
    );
  }

  getMessage(id: string): Observable<Message> {
    return this.http.get<Message>(`${API_ENDPOINTS.MESSAGES}/${id}`).pipe(
      map(msg => ({
        ...msg,
        timestamp: new Date(msg.timestamp)
      })),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An error occurred';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => errorMessage);
  }
}
