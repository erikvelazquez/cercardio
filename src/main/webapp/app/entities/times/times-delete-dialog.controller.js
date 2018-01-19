(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('TimesDeleteController',TimesDeleteController);

    TimesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Times'];

    function TimesDeleteController($uibModalInstance, entity, Times) {
        var vm = this;

        vm.times = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Times.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
