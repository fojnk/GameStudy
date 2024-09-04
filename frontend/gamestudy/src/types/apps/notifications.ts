export interface NotificationType {
    id?: number,
    title?: string,
    details?: string,
    recipient_ids?: number[],
    recipient_confirmed_ids?: number[],
    sender_id?: number;
}