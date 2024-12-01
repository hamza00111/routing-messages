import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {Partner} from "../models/partner";
import {API_ENDPOINTS} from "../../core/constants/api.constants";
import {catchError} from "rxjs/operators";
import {PaginatedResponse} from "../../shared/models/paginated-response.model";

@Injectable({
  providedIn: 'root'
})
export class PartnerService {
  constructor(private http: HttpClient) {
  }

  getPartners(page: number = 0, size: number = 10): Observable<PaginatedResponse<Partner>> {
    const params = new HttpParams()
      .set('offset', page.toString())
      .set('limit', size.toString());
    return this.http.get<PaginatedResponse<Partner>>(API_ENDPOINTS.PARTNERS, {params})
      .pipe(catchError(this.handleError));
  }

  createPartner(partner: Partner): Observable<Partner> {
    return this.http.post<Partner>(`${API_ENDPOINTS.PARTNERS}/create`, partner)
      .pipe(catchError(this.handleError));
  }

  deletePartner(id: string): Observable<void> {
    return this.http.delete<void>(`${API_ENDPOINTS.PARTNERS}/${id}`)
      .pipe(catchError(this.handleError));
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
