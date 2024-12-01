import { Component } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {Direction, ProcessedFlowType} from "../models/partner";
import {PartnerService} from "../services/partner.service";
import {MatButtonModule} from "@angular/material/button";
import {MatSelectModule} from "@angular/material/select";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-add-partner',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule
  ],
  templateUrl: './add-partner.component.html',
  styleUrl: './add-partner.component.scss'
})
export class AddPartnerComponent {

  partnerForm: FormGroup;
  directions: Direction[] = ['INBOUND', 'OUTBOUND'];
  flowTypes: ProcessedFlowType[] = ['MESSAGE', 'ALERTING', 'NOTIFICATION'];
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private partnerService: PartnerService,
    public dialogRef: MatDialogRef<AddPartnerComponent>
  ) {
    this.partnerForm = this.fb.group({
      alias: ['', [Validators.required]],
      type: ['', [Validators.required]],
      direction: ['', [Validators.required]],
      application: [''],
      processedFlowType: ['', [Validators.required]],
      description: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.partnerForm.valid) {
      this.partnerService.createPartner(this.partnerForm.value).subscribe({
        next: () => {
          this.dialogRef.close(true);
        },
        error: (error) => {
          this.error = error;
        }
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
