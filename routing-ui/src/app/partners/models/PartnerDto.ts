import {Direction, ProcessedFlowType} from "./partner";

export interface PartnerDto {
  id: string
  alias: string;
  type: string;
  direction: Direction;
  application?: string;
  processedFlowType: ProcessedFlowType;
  description: string;
}
