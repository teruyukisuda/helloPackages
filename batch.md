### 処理対象のデータ(List<JobSearch>)取得
```
SELECT job_id, card_id, slot_number FROM job_search
WHERE
  wait_automatic_confirm == TRUE
  AND
  automatic_confirm_date <= CURRENT_TIMESTAMP
```
ここで取得したデータをそれぞれ
```
job_id_1
card_id_1
slot_number_1
```
とする


### それぞれのJobSearchデータに対して下記の処理を実施する(トランザクション境界）
* select for update 
```
SELECT active_catd_id FROM job WHERE job_id = [job_id_1] FOR UPDATE;
```
ここで取得したデータを
```
active_card_id_1
```
とする


* card_id_1 != active_card_id_1
    * 何もせず処理終了
* [card_id_1] == [active_card_id_1]
  ```
  SELECT is_active FROM job_search
  WHERE job_id = job_id_1 AND card_id = card_id_1 AND slot_number = slot_number_1
  ```
  * is_active == false
    * なにもせず処理終了
  * is_active == true
    * job_execution_log登録
    ```
    insert job_execution_log values(processorからの値)
    ```
    * job_search更新
    * processorからJobSearchの更新の値
    ```
    is_executed                 = FALSE
    is_active                   = FALSE
    wait_automatic_confirm      = FALSE
    automatic_confirm_date      = [JobflowProcessorが返却した日時]
    executed_user_id            = [JobflowProcessorが返却した実行者のUUID]
    executed_user_name          = [JobflowProcessorが返却した実行者名]
    executed_user_position_id   = [JobflowProcessorが返却した実行者の所属UUID文字列]
    executed_user_position_name = [JobflowProcessorが返却した実行者の所属表記]
    ```
    * is_active = trueに更新？　trueの場合に処理するんじゃないの？なのに更新？
    * もし、『次にアクティブとなるカード』が『自動確認有り』だった場合は、以下のカラムも更新する。不明
    
    * jobを更新
    ```
    job_status              = [JobflowProcessorが返却]
    workflow_status         = [JobflowProcessorが返却]
    has_approval_task       = [JobflowProcessorが返却]
    active_card_id          = [JobflowProcessorが返却]
    active_card_task_kind   = [JobflowProcessorが返却]
    received_date           = [JobflowProcessorが返却]
    executed_date           = [JobflowProcessorが返却]
    order_of_cards_on_route = [JobflowProcessorが返却]
    count_of_cards_on_route = [JobflowProcessorが返却]
    visible_order_text      = [JobflowProcessorが返却]
    last_approval_date      = [JobflowProcessorが返却]
    finish_date             = [JobflowProcessorが返却]
    ```
