# branch構成

* develop(最新の開発ブランチ)
* feature-xxx(各開発ブランチ)
* 0.x.x(0.1.xや0.2.xなど バグフィックス用のリリースブランチ)
    * 0.1.x(0.1系のリリースブランチ)
    * 0.2.x(0.2系のリリースブランチ)

# 開発方法

## 自動テスト

プルリクエストが作成または更新され、Javaソースファイルが変更された場合、`.github/workflows/test.yml`で定義された自動テストワークフローが実行されます。このワークフローは以下の処理を行います：

1. リポジトリのチェックアウト
2. JDK 21のセットアップ
3. Mavenテストの実行

このワークフローにより、コードの変更がプロジェクトの既存機能を壊していないことが確認されます。

### 新機能開発

* ブランチ作成
    * developからfeatureブランチを作成
* 開発完了
    * developに対してPR作成
    * PR作成時に自動テスト実行
    * レビュー後merge

### bugfix開発(リリースブランチごととdevelopに対して同じことをする)(競合が多い場合)

* ブランチ作成
    * リリースブランチ（0.1.xなど）からfeatureブランチ作成
* 開発完了
    * リリースブランチに対してPR作成
    * PR作成時に自動テスト実行
    * レビュー後リリースブランチにmerge
    * developブランチに対しても別PRを作成し、レビュー後developブランチにもmerge(
      ここではdevelopブランチからfeatureを作成しておく)

### bugfixはcherry-pickするケース(競合が少ない場合)

* ブランチ作成
    * 最も古いバージョンのリリースブランチからfeatureブランチ作成
* 開発完了
    * 対象のリリースブランチに対してPR作成
    * PR作成時に自動テスト実行
    * レビュー後対象のリリースブランチにmerge
    * そのマージコミットではなく、featureブランチの修正コミットを他のリリースブランチとdevelopにcherry-pickする
    ```
    マージコミットをチェリーピックするよりも、
    実際のバグ修正を含む個別のコミット（featureブランチ上の修正コミット）
    をチェリーピックする

    マージコミットにはマージ自体の情報が含まれており、
    純粋な修正コードだけでなく余分な情報も含まれいる

    個別の修正コミットの方が変更内容が明確で、
    チェリーピック時の競合も把握しやすい
    複数の修正がある場合、必要な修正だけを選択的にチェリーピック

    修正内容が複数のコミットに分かれている場合は、
    それらをまとめてチェリーピックするか、
    もしくはsquashコミットを作成してからチェリーピックする
    ```

# バージョニングと開発プロセス

このプロジェクトでは、開発中は常にSNAPSHOTバージョンを使用します。例えば、`0.1.9-SNAPSHOT`のように、バージョン番号に`-SNAPSHOT`サフィックスが付いています。

# バージョンサポート
* 基本的にworkflowproプロジェクトからのみ使用される前提のため最新バージョンのみをサポート対象とする
* マイナーバージョンが上がった時点でそのマイナーバージョンのみをサポート対象
* 使用するモジュールはバージョンアップに合わせた対応を実施すること

## SNAPSHOTバージョンについて

* 開発中は常にSNAPSHOTバージョンを使用します
* SNAPSHOTバージョンは不安定な開発中のバージョンを示します
* SNAPSHOTバージョンモジュールの変更タイミングは特に決まっていない(使用する場合は注意)
* 正式リリース時にのみSNAPSHOTサフィックスが削除されます

## リリースプロセス

リリースプロセスは3つのGitHub Actionsワークフローを使用して行われます：

### 1. リリース準備（pre-release.yml）

リリース準備は`.github/workflows/pre-release.yml`で定義されたワークフローを使用して行われます。  
このワークフローは手動でトリガーされ、以下の処理を行います：

1. developブランチから最新のコードを取得
2. 現在のバージョン（例：0.1.15-SNAPSHOT）からSNAPSHOTサフィックスを削除
3. リリースブランチ（例：release/0.1.15）を作成
4. pom.xmlのバージョンを更新（SNAPSHOTサフィックスを削除）
5. 変更をコミットしてリリースブランチにプッシュ
6. mainブランチに対するプルリクエストを作成

リリース準備ワークフローを実行するには以下のコマンドを使用します：

```
gh workflow run pre-release.yml
```

### 2. リリース実行（release.yml）

リリース実行は`.github/workflows/release.yml`で定義されたワークフローを使用して行われます。このワークフローは、リリース準備で作成されたプルリクエストがmainブランチにマージされた時に自動的にトリガーされます。以下の処理を行います：

1. mainブランチから最新のコードを取得
2. pom.xmlからリリースバージョンを取得
3. リリースタグを作成してプッシュ
4. Mavenでプロジェクトをビルド
5. GitHubリリースを作成し、以下のアセットを含める：
   - hello-packages-{バージョン}.jar
   - hello-packages-{バージョン}-sources.jar
6. GitHubパッケージにアーティファクトをデプロイ

リリースワークフローはプルリクエストのマージ時に自動的に実行されますが、コミットメッセージに「Prepare release」が含まれている必要があります。

### 3. リリース後処理（post-release.yml）

リリース後処理は`.github/workflows/post-release.yml`で定義されたワークフローを使用して行われます。このワークフローは、リリース実行ワークフローが完了した後に自動的にトリガーされます。以下の処理を行います：

1. developブランチから最新のコードを取得
2. mainブランチの変更をdevelopブランチにマージ
3. 次の開発バージョンを設定（パッチバージョンをインクリメントしてSNAPSHOTサフィックスを追加）
4. 変更をコミットしてdevelopブランチにプッシュ

このワークフローにより、リリース後にdevelopブランチが自動的に更新され、次の開発サイクルの準備が整います。

### リリースステータスの確認

最新のリリース（v0.1.9）は正常に完了しました。リリースプロセスが更新され、今後のリリースでは以下のアセットのみが生成されます：

- hello-packages-{バージョン}-sources.jar
- hello-packages-{バージョン}.jar

※javadoc.jar、zip、tar.gzファイルは生成されなくなりました。

#### リリースワークフローの実行状況確認

リリース準備ワークフローの実行状況は以下のコマンドで確認できます：

```
gh run list --workflow=pre-release.yml --limit 1
```

リリース実行ワークフローの実行状況は以下のコマンドで確認できます：

```
gh run list --workflow=release.yml --limit 1
```

特定のワークフロー実行の詳細を確認するには、実行IDを指定します：

```
gh run view <実行ID>
```

失敗したステップのログを確認するには：

```
gh run view <実行ID> --log-failed
```

#### リリースの確認

作成されたリリースは以下のコマンドで確認できます：

```
gh release view v{バージョン番号}
```

#### プルリクエストの確認

リリース準備ワークフローで作成されたプルリクエストは以下のコマンドで確認できます：

```
gh pr list
```
