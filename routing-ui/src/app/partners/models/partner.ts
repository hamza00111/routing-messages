export type Direction = 'INBOUND' | 'OUTBOUND';
export type ProcessedFlowType = 'MESSAGE' | 'ALERTING' | 'NOTIFICATION';

export interface Partner {
  id: string;
  alias: string;
  type: string;
  direction: Direction;
  application?: string;
  processedFlowType: ProcessedFlowType;
  description: string;
}
