(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('AgentMasterController', AgentMasterController);

    AgentMasterController.$inject = ['AgentMaster'];

    function AgentMasterController(AgentMaster) {

        var vm = this;

        vm.agentMasters = [];

        loadAll();

        function loadAll() {
            AgentMaster.query(function(result) {
                vm.agentMasters = result;
                vm.searchQuery = null;
            });
        }
    }
})();
