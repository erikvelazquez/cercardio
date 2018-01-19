(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PrivateHealthInsuranceController', PrivateHealthInsuranceController);

    PrivateHealthInsuranceController.$inject = ['PrivateHealthInsurance', 'PrivateHealthInsuranceSearch'];

    function PrivateHealthInsuranceController(PrivateHealthInsurance, PrivateHealthInsuranceSearch) {

        var vm = this;

        vm.privateHealthInsurances = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            PrivateHealthInsurance.query(function(result) {
                vm.privateHealthInsurances = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PrivateHealthInsuranceSearch.query({query: vm.searchQuery}, function(result) {
                vm.privateHealthInsurances = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
