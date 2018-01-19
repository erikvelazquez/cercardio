(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('SocialHealthInsuranceController', SocialHealthInsuranceController);

    SocialHealthInsuranceController.$inject = ['SocialHealthInsurance', 'SocialHealthInsuranceSearch'];

    function SocialHealthInsuranceController(SocialHealthInsurance, SocialHealthInsuranceSearch) {

        var vm = this;

        vm.socialHealthInsurances = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            SocialHealthInsurance.query(function(result) {
                vm.socialHealthInsurances = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SocialHealthInsuranceSearch.query({query: vm.searchQuery}, function(result) {
                vm.socialHealthInsurances = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
