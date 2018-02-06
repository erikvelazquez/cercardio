(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Indigenous_LanguagesDetailController', Indigenous_LanguagesDetailController);

    Indigenous_LanguagesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Indigenous_Languages'];

    function Indigenous_LanguagesDetailController($scope, $rootScope, $stateParams, previousState, entity, Indigenous_Languages) {
        var vm = this;

        vm.indigenous_Languages = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:indigenous_LanguagesUpdate', function(event, result) {
            vm.indigenous_Languages = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
