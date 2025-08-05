const connectBtn = document.getElementById('connectBtn');
const loginBtn = document.getElementById('signBtn');  // 需要你增加一个登录按钮
const refreshBtn = document.getElementById('refreshBtn');  // 需要你增加一个登录按钮
const log = document.getElementById('log');

let userAddress = null;

const BASE_URL = 'http://localhost:8091';
// 步骤1：连接钱包
connectBtn.addEventListener('click', async () => {
    if (!window.ethereum) {
        log.textContent = "请安装 MetaMask 扩展插件";
        return;
    }
    try {
        const accounts = await window.ethereum.request({ method: 'eth_requestAccounts' });
        userAddress = accounts[0];
        // 步骤2：获取签名信息
        const messageResp = await fetch(`${BASE_URL}/public/get/message/${userAddress}`);
        console.log("messageResp: ", messageResp);
        const messageData = await messageResp.json();
        console.log("messageData", messageData);
        const message = messageData.msg;

        // 步骤3：钱包签名
        const signature = await window.ethereum.request({
            method: 'personal_sign',
            params: [message, userAddress],
        });

        console.log("signature: ", signature);
        // 步骤4：发送签名结果到后端验证
        const loginResp = await fetch(`${BASE_URL}/public/login`, {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ address: userAddress, sign: signature })
        });
        const loginResult = await loginResp.json();

        if (loginResult.code === 200) {
            const token = loginResult.data?.token || loginResult.token;
            connectBtn.textContent = `${userAddress.slice(0, 6)}...${userAddress.slice(-4)}`;

        } else {

        }

    } catch (err) {
    }
});

refreshBtn.addEventListener('click', async () => {
    const res = await fetch(`${BASE_URL}/public/refreshLark`);

    console.log("refresh: ", res);
    await loadTokenList()
})

const tokenSelect = document.getElementById("tokenSelect");
const tableBody = document.querySelector("#coinTable tbody");
const loadBtn = document.getElementById("loadDataBtn");

// 加载代币名称列表
async function loadTokenList() {
    const res = await fetch(`${BASE_URL}/public/coinList`);
    console.log("res", res);
    const tokens = await res.json();

    tokenSelect.innerHTML = '<option value="">-- 请选择代币1 --</option>';
    tokens.data.forEach(token => {
        const option = document.createElement("option");
        option.value = token;
        option.textContent = token;
        tokenSelect.appendChild(option);
    });
}

// 根据选中的代币获取数据
async function loadTokenData() {
    const selected = tokenSelect.value;

    const res = await fetch(`${BASE_URL}/public/query`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ coinName: selected })
    });
    const data = await res.json();
    const insertData = data.data;
    console.log("insertData", insertData);
    tableBody.innerHTML = "";

    insertData.forEach(row => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
<!--          <td>${row["projectName"] || ""}</td>-->
          <td>${row["coinName"] || ""}</td>
          <td>${row["price"] || ""}</td>
          <td>${row["okxTwap"] || ""}</td>
          <td>${row["okxMa"] || ""}</td>
          <td>${row["bnTwap"] || ""}</td>
          <td>${row["bnMa"] || ""}</td>
        `;
        tableBody.appendChild(tr);
    });
}

// 初始化
loadTokenList();
loadBtn.addEventListener("click", loadTokenData);