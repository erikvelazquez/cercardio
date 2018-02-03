(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Indigenous_LanguagesDeleteController',Indigenous_LanguagesDeleteController);

    Indigenous_LanguagesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Indigenous_Languages'];

    function Indigenous_LanguagesDeleteController($uibModalInstance, entity, Indigenous_Languages) {
        var vm = this;

        vm.indigenous_Languages = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Indigenous_Languages.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
